import Image from 'next/image';
import AppLogo from '@/assets/images/app-logo.png';
import FacebookIcon from '@/assets/icons/facebook-icon.svg';
import TwitterIcon from '@/assets/icons/twitter-icon.svg';
import LinkedInIcon from '@/assets/icons/linkedin-icon.svg';
import InstagramIcon from '@/assets/icons/instagram-icon.svg';
import YoutubeIcon from '@/assets/icons/youtube-icon.svg';
import GitHubIcon from '@/assets/icons/github-icon.svg';
import LocationIcon from '@/assets/icons/location-icon.svg';
import PhoneIcon from '@/assets/icons/phone-icon.svg';
import WebsiteIcon from '@/assets/icons/website-icon.svg';
import EmailIcon from '@/assets/icons/email-icon.svg';

const AppFooter = (): React.ReactElement => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="inline-flex px-5 py-3 bg-[rgba(20,177,231,.1)]">
      <div className="w-[40%]">
        <div className="px-16">
          DreamHigh is a Cong Thanh&apos;s personal project, including an e-commerce site, official website, employee management page and administrator&apos;s site. Specific
          description at &nbsp;
          <a href="https://github.com/pdcthanh112/MyDream.git" className="underline">
            GitHub
          </a>
        </div>
        <div className="flex justify-center">
          <Image src={AppLogo} alt="App logo" style={{ width: '30%', height: 'auto' }} className="flex justify-center" />
        </div>
        <div className="flex justify-center">Connect to me</div>
        <div className="w-full inline-flex justify-center">
          <a href="https://www.facebook.com/pdcthanh112/">
            <Image src={FacebookIcon} alt="Facebook icon" style={{ width: '2rem', height: 'auto' }} className="mx-2" />
          </a>
          <a href="https://www.linkedin.com/in/pdcthanh112dev/">
            <Image src={LinkedInIcon} alt="LinkedIn icon" style={{ width: '2rem', height: 'auto' }} className="mx-2" />
          </a>
          <a href="https://www.linkedin.com/in/pdcthanh112dev/">
            <Image src={TwitterIcon} alt="Twitter icon" style={{ width: '2rem', height: 'auto' }} className="mx-2" />
          </a>
          <a href="https://www.linkedin.com/in/pdcthanh112dev/">
            <Image src={InstagramIcon} alt="Instagram icon" style={{ width: '2rem', height: 'auto' }} className="mx-2" />
          </a>
          <a href="https://www.linkedin.com/in/pdcthanh112dev/">
            <Image src={YoutubeIcon} alt="Youtube icon" style={{ width: '2rem', height: 'auto' }} className="mx-2" />
          </a>
          <a href="https://github.com/pdcthanh112/">
            <Image src={GitHubIcon} alt="Youtube icon" style={{ width: '2rem', height: 'auto' }} className="mx-2" />
          </a>
        </div>
        <div className="flex justify-center">&copy;{currentYear} by pdcthanh</div>
      </div>
      <div className="w-[15%]">
        <ul className="text-[#626d79]">
          <li>Java Spring framework</li>
          <li>NodeJS Express</li>
          <li>Python Django</li>
          <li>PostgreSQL</li>
          <li>MySQL</li>
          <li>MongoDB</li>
          <li>Redis</li>
          <li>Elasticsearch</li>
        </ul>
      </div>
      <div className="w-[15%]">
        <ul className="text-[#626d79]">
          <li>React</li>
          <li>NextJS</li>
          <li>Angular</li>
          <li>Flutter</li>
          <li>React Native</li>
          <li>AWS</li>
          <li>Firebase</li>
        </ul>
      </div>
      <div className="w-[30%]">
        <div className="flex items-center py-2">
          <Image src={LocationIcon} alt="Location icon" style={{ width: '1.2rem', height: '1.2rem' }} />
          <span className="ml-2">285 CMT8 Street, Ward 12, District 10, Ho Chi Minh City</span>
        </div>
        <div className="flex items-center py-2">
          <Image src={PhoneIcon} alt="Phone icon" style={{ width: '1.2rem', height: '1.2rem' }} />
          <span className="ml-2">(+84) 382-722-849</span>
        </div>
        <div className="flex items-center py-2">
          <Image src={WebsiteIcon} alt="Website icon" style={{ width: '1.2rem', height: '1.2rem' }} />
          <span className="ml-2">mydream.com.vn</span>
        </div>
        <div className="flex items-center py-2">
          <Image src={EmailIcon} alt="Email icon" style={{ width: '1.2rem', height: '1.2rem' }} />
          <span className="ml-2">pdcthanh112.dev@gmail.com</span>
        </div>
      </div>
    </footer>
  );
};

export default AppFooter;
