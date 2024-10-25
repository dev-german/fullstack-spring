import axios from "axios";

const getAuthConfig = () => ({
  headers: {
    Authorization: `Bearer ${localStorage.getItem("access_token")}`,
  },
})

export const getCustomers = async () => {
  try {
    return await axios.get(
      `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
      getAuthConfig()

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
      `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`,
      getAuthConfig()
    );
  } catch (err) {
    console.log(err)
    throw err;
  }
}

export const updateCustomer = async (id, update) => {
  try {
    return await axios.put(
      `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`,
      update,
      getAuthConfig()
    );
  } catch (err) {
    console.log(err) 
    throw err;
  }
}

export const login = async (usernameAndPassword) => {
  try {
    return await axios.post(
      `${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`,
      usernameAndPassword
    );
  } catch (err) {
    console.log(err)
    throw err;
  }
}

export const uploadCustomerProfilePicture = async (id, formData) => {
  try {
    return await axios.post(
      `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}/profile-image`,
      formData,
      {
        ...getAuthConfig(),
        'Content-Type': 'multipart/form-data'
      }
    );
  } catch (err) {
    console.log(err)
    throw err;
  }
}

export const customerProfilePictureUrl = (id) => {
  const baseUrl = `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}/profile-image`
  console.log(baseUrl)
  return baseUrl
}