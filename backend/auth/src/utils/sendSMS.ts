import twilio from 'twilio';

const accountSid = process.env.TWILIO_ACCOUNT_SID;
const authToken = process.env.TWILIO_AUTH_TOKEN;
const twilioPhoneNumber = process.env.TWILIO_PHONE_NUMBER;

const client = twilio(accountSid, authToken);

const sendSMS = async (content: string, toNumber: string) => {
  try {
    await client.messages
      .create({
        body: `${content}`,
        to: toNumber,
        from: twilioPhoneNumber,
      })
      .then(message => console.log(message));
  } catch (error) {
    console.log(error);
  }
};

export default sendSMS;
