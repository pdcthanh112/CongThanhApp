import nodemailer from 'nodemailer';

const sendEmail = async (email: string, subject: string, content: string) => {
  try {
    const transporter = nodemailer.createTransport({
      host: process.env.MAIL_HOST,
      service: process.env.MAIL_SERVICE,
      port: Number(process.env.MAIL_PORT),
      secure: Boolean(process.env.MAIL_SECURE),
      auth: {
        user: process.env.MAIL_USER,
        pass: process.env.MAIL_APP_PASSWORD,
      },
    });

    await transporter.sendMail({
      from: {
        name: 'CongThanh Ecommerce App',
        address: process.env.MAIL_USER,
      },
      to: email,
      subject: subject,
      // text: content,
      html: content,
    });
    console.log('email sent successfully');
  } catch (error) {
    console.log('email not sent!');
    console.log(error);
  }
};

export default sendEmail;
