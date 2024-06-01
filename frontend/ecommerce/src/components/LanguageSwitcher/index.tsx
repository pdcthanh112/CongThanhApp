import Image from 'next/image';
import { Card, FormControl, FormControlLabel, FormLabel, Radio, RadioGroup } from '@mui/material';
import { useRouter } from 'next/navigation';
import USFlag from '@/assets/icons/us-flag.png';
import VNFlag from '@/assets/icons/vn-flag.png';
import CNFlag from '@/assets/icons/china-flag.png';
import ESFlag from '@/assets/icons/spain-flag.png';
import FRFlag from '@/assets/icons/france-flag.png';
import DEFlag from '@/assets/icons/germany-flag.png';
import ITFlag from '@/assets/icons/italy-flag.png';
import KRFlag from '@/assets/icons/korea-flag.png';
import RUFlag from '@/assets/icons/russia-flag.png';

export default function ChangeLanguage() {
  const languageData = [
    { key: 'en', name: 'en-US', flag: USFlag },
    { key: 'vi', name: 'vi-VN', flag: VNFlag },
    { key: 'zh', name: 'zh-CN', flag: CNFlag },
    { key: 'es', name: 'es-ES', flag: ESFlag },
    { key: 'fr', name: 'fr-FR', flag: FRFlag },
    { key: 'de', name: 'de-DE', flag: DEFlag },
    { key: 'it', name: 'it-IT', flag: ITFlag },
    { key: 'ko', name: 'ko-KR', flag: KRFlag },
    { key: 'ru', name: 'ru-RU', flag: RUFlag },
  ];
  const router = useRouter();

  return (
    <div className="relative inline-block group">
      <div className="hover:cursor-pointer flex">
        <Image src={languageData.find((item) => item.key === router.locale)?.flag || USFlag} alt="fldass" width={25} />
        <span className="ml-1 font-semibold flex justify-center items-center">{router.locale?.toUpperCase()}</span>
      </div>
      <Card className="text-[#a4a4a4] text-sm hidden absolute transform -translate-x-2/3 p-4 w-[10rem] group-hover:block group-hover:z-50">
        <FormControl>
          <FormLabel>Choose language</FormLabel>
          <RadioGroup
            defaultValue={router.locale}
            name="choose-language"
            onChange={(e) => {
              router.push(
                {
                  pathname: router.pathname,
                  query: router.query,
                },
                undefined,
                { locale: e.target.value },
              );
            }}>
            {languageData.map((item) => (       
                <FormControlLabel
                  key={item.key}
                  value={item.key}
                  control={<Radio size="small" />}
                  label={
                    <div className="flex items-center">
                      <Image src={item.flag} alt={''} width={25} />
                      <span className="ml-3">{item.name}</span>
                    </div>
                  }
                />         
            ))}
          </RadioGroup>
        </FormControl>
      </Card>
    </div>
  );
}
