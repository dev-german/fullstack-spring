import axios from "axios";

export const getCustomers = async () => {
  try {
    return await axios.get(
      `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`
    );
  } catch (err) {
    console.log(err)
    throw err;
  }
}

export const saveCustomer = async (customer) => {
  try {
    return await axios.post(
      `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
      customer
    );
  } catch (err) {
    console.log(err)
    throw err;
  }
}

export const deleteCustomer = async (id) => {
  try {
    return await axios.delete(
      `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`
    );
  } catch (err) {
    console.log(err)
    throw err;
  }
}
