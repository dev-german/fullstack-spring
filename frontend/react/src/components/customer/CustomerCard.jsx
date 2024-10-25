import {
  Heading,
  Avatar,
  Box,
  Center,
  Image,
  Flex,
  Text,
  Stack,
  useColorModeValue,
  Tag,
  Button,
  AlertDialog,
  AlertDialogOverlay,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogBody,
  AlertDialogFooter,
  useDisclosure,
} from "@chakra-ui/react";
import React from "react";
import { customerProfilePictureUrl, deleteCustomer } from "../../services/client";
import {
  errorNotification,
  successNotification,
} from "../../services/notification";
import UpdateCustomerDrawer from "./UpdateCustomerDrawer";

export default function CardWithImage({
  id,
  name,
  email,
  age,
  gender,
  imageNumber,
  fetchCustomers,
}) {
  const genderImage = gender === "MALE" ? "men" : "women";
  const { isOpen, onOpen, onClose } = useDisclosure();
  const cancelRef = React.useRef();

  return (
    <Center py={6}>
      <Box
        maxW={"300px"}
        minW={"300px"}
        w={"full"}
        m={2}
        bg={useColorModeValue("white", "gray.800")}
        boxShadow={"lg"}
        rounded={"md"}
        overflow={"hidden"}
      >
        <Image
          h={"120px"}
          w={"full"}
          src={
            "https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"
          }
          objectFit="cover"
          alt="#"
        />
        <Flex justify={"center"} mt={-12}>
          <Avatar
            size={"xl"}
            src={customerProfilePictureUrl(id)}
            alt={"Avatar"}
            css={{
              border: "2px solid white",
            }}
          />
        </Flex>

        <Box p={6}>
          <Stack spacing={2} align={"center"} mb={5}>
            <Tag borderRadius={"full"}>{id}</Tag>
            <Heading fontSize={"2xl"} fontWeight={500} fontFamily={"body"}>
              {name}
            </Heading>
            <Text color={"gray.500"}>{email}</Text>
            <Text color={"gray.500"}>
              Age {age} | {gender}
            </Text>
          </Stack>
        </Box>
        <Stack direction={"row"} justify={"center"} spacing={6} p={4}>
          <Stack>
            <UpdateCustomerDrawer
              initialValues={{ name, email, age, gender }}
              customerId={id}
              fetchCustomers={fetchCustomers}
            />
          </Stack>
          <Stack>
            <Button
              bg={"red.400"}
              color={"white"}
              rounded={"full"}
              _hover={{
                transform: "translateY(-2px)",
                boxShadow: "lg",
              }}
              _focus={{
                bg: "grey.500",
              }}
              onClick={onOpen}
            >
              Delete
            </Button>
            <AlertDialog
              isOpen={isOpen}
              leastDestructiveRef={cancelRef}
              onClose={onClose}
            >
              <AlertDialogOverlay>
                <AlertDialogContent>
                  <AlertDialogHeader fontSize="lg" fontWeight="bold">
                    Delete Customer
                  </AlertDialogHeader>

                  <AlertDialogBody>
                    Are you sure you want to {name}? You can't undo this
                    operation
                  </AlertDialogBody>

                  <AlertDialogFooter>
                    <Button ref={cancelRef} onClick={onClose}>
                      Cancel
                    </Button>
                    <Button
                      colorScheme="red"
                      onClick={() => {
                        deleteCustomer(id)
                          .then((res) => {
                            console.log(res);
                            successNotification(
                              "Customer deleted",
                              `${name} was successfully deleted`
                            );
                            fetchCustomers();
                          })
                          .catch((err) => {
                            console.log(err);
                            errorNotification(
                              err.code,
                              err.response.data.message
                            );
                          })
                          .finally(() => {
                            onClose();
                          });
                      }}
                      ml={3}
                    >
                      Delete
                    </Button>
                  </AlertDialogFooter>
                </AlertDialogContent>
              </AlertDialogOverlay>
            </AlertDialog>
          </Stack>
        </Stack>
      </Box>
    </Center>
  );
}
