import React from 'react'
import { Box } from '@mui/material'
import FieldInput from '../../../features/product/component/FieldInput'

const VariantSession = () => {
  return (
    <Box>VariantSession

      <FieldInput name='sku' title='SKU'/>
      <FieldInput name='gtin' title='GTIN'/>
    </Box>
  )
}

export default VariantSession