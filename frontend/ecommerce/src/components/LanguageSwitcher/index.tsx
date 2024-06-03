import React from "react";
import Image from "next/image";
import { useRouter } from "next/navigation";
import LanguageData from "../../../public/data/languages.json";
import USFlag from "@/assets/icons/us-flag.png";
import VNFlag from "@/assets/icons/vn-flag.png";
import CNFlag from "@/assets/icons/china-flag.png";
import ESFlag from "@/assets/icons/spain-flag.png";
import FRFlag from "@/assets/icons/france-flag.png";
import DEFlag from "@/assets/icons/germany-flag.png";
import ITFlag from "@/assets/icons/italy-flag.png";
import KRFlag from "@/assets/icons/korea-flag.png";
import RUFlag from "@/assets/icons/russia-flag.png";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "../ui/dialog";

export default function ChangeLanguage() {
  const router = useRouter();

  return (
    <React.Fragment>
      <Dialog>
        <DialogTrigger asChild>
          <div className="hover:cursor-pointer flex">
            <Image src={USFlag} alt="" width={25} />
            <span className="ml-1 font-semibold flex justify-center items-center">En</span>
          </div>
        </DialogTrigger>
        <DialogContent className="w-[1250px] max-w-[1500px] h-2/3">
          <DialogHeader>
            <DialogTitle>Choose language</DialogTitle>
            <DialogDescription>Please choose your language.</DialogDescription>
          </DialogHeader>
          <div className="overflow-y-scroll">
            <div className="grid grid-cols-12 gap-4 py-4">
              {LanguageData.map((item) => (
                <div
                  key={item.code}
                  className="col-span-2 hover:bg-gray-200 rounded-sm truncate px-3 py-4 hover:cursor-pointer"
                >
                  <div>{item.language.name}</div>
                  <div className="font-light text-sm">{item.language.nativeName}</div>
                </div>
              ))}
            </div>
          </div>
        </DialogContent>
      </Dialog>
    </React.Fragment>
  );
}
