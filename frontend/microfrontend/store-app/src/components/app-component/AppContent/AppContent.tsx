import { Box } from '@mui/material'
import React from 'react'

type PropsType = {
    children: React.ReactElement
}

const AppContent = ({children}: PropsType) => {
  return (
    <Box sx={{minHeight: '80vh'}}>{children}</Box>
  )
}

export default AppContent