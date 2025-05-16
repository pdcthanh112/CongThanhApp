import React, { useEffect, useState } from 'react';

const ChatComponent = () => {

  const [stock, setStock] = useState();

  useEffect(() => {
    const socket = new WebSocket('ws://your-server/ws');
    socket.onmessage = (event) => {
      const newStock = JSON.parse(event.data);
      setStock(newStock);
    };
    socket.onclose = () => console.log('WebSocket closed');
    return () => socket.close();
  }, []);

  console.log('SSSSSSSSSSSSSS', stock)
  
  return <div>ChatComponent</div>;
};

export default ChatComponent;
