import axios from "axios";

export const getCustomers = async () => {
  try {
    return await axios.get(
      `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`
    );
  } catch (err) {
    throw err;
  }
};

export const saveCustomer = async (customer) => {
  try {
    return await axios.post(
      `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
      customer
    );
  } catch (err) {
    throw err;
  }
};
