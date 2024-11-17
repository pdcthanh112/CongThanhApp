import axiosConfig from '@/config/axiosConfig';

export const executePaypalPayment = async (paymentId: string, payerId: string) => {
  await axiosConfig
    .post('/payment/paypal/execute', {
      paymentId,
      payerId,
    })
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};
