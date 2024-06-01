 
import React from 'react'

interface LayoutProps {
    children: React.ReactNode
}

const EmptyLayout = ({children} : LayoutProps) => {
  return (
    <div>{children}</div>
  )
}

export default EmptyLayout;
