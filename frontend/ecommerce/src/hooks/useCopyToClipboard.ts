import { SetStateAction, useState } from "react"
import copy from "copy-to-clipboard"

export default function useCopyToClipboard() {
    const [value, setValue] = useState()
    const [success, setSuccess] = useState()

    const copyToClipboard = (text: any, options: any) => {
        const result: any = copy(text, options)
        if (result) setValue(text)
        setSuccess(result)
    }

    return [copyToClipboard, { value, success }]
}


// cach dung:
// import useCopyToClipboard from "./useCopyToClipboard"

// export default function CopyToClipboardComponent() {
//     const [copyToClipboard, { success }] = useCopyToClipboard()

//     return (
//         <>
//             <button onClick={() => copyToClipboard("This was copied")}>
//                 {success ? "Copied" : "Copy Text"}
//             </button>
//             <input type="text" />
//         </>
//     )
// }