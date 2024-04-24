import React, { useState } from 'react';
import { useAppSelector } from '@redux/store';
import { useQuery } from '@tanstack/react-query';
import { getAddressByCustomer } from 'api/addressApi';
import { Modal, Tabs, type TabsProps, Radio, Button } from 'antd';
import TabCreateAddress from './TabCreateAddress';
import TabEditAddress from './TabEditAddress';
import { Icon } from '@mui/material';
import { Add } from '@mui/icons-material';
import { Address } from '@models/type';

type PropsType = {
  isOpen: boolean;
  handleOpen: (isOpen: boolean) => void;
  changeAddress: (address: Address) => void;
};

const SelectAddress = ({ isOpen, handleOpen, changeAddress }: PropsType) => {
  const currentUser = useAppSelector((state) => state.auth.currentUser);

  const [activeTab, setActiveTab] = useState({ currentTab: '1', addressId: 0 });
  const [address, setAddress] = useState<Address>();

  const { data: listAddress } = useQuery(['address'], async () => await getAddressByCustomer(currentUser.userInfo.accountId).then((response) => response.data));

  const handleChangeAddress = () => {
    if (address) {
      changeAddress(address);
      handleOpen(false)
    }
  };

  const TabShowAddress = () => {
    return (
      <React.Fragment>
        <h3>Your address</h3>
        <Radio.Group defaultValue={listAddress.find((address: Address) => address.isDefault === true)} onChange={(event) => setAddress(event.target.value)}>
          {listAddress.map((item: Address) => (
            <Radio key={item.id} value={item} checked={item.isDefault}>
              <div className="my-2 px-3 py-2 border-t-2">
                <div>
                  <span className="mr-1 text-lg">Cong Thanh</span>|<span className="ml-2">{item.phone}</span>
                </div>
                <div className="flex justify-between">
                  <p className="w-4/5">
                    {item.street}, {item.addressLine3}, {item.addressLine2}, {item.addressLine1}, {item.country}
                  </p>
                  <span className="text-blue-400" onClick={() => setActiveTab({ currentTab: '3', addressId: item.id })}>
                    Edit
                  </span>
                </div>
                {item.isDefault && <span className="bg-yellow-300 px-2 py-1 rounded-md">Default</span>}
              </div>
            </Radio>
          ))}
        </Radio.Group>

        <Button onClick={() => setActiveTab({ currentTab: '2', addressId: -1 })} className="bg-yellow-300">
          <Icon component={Add} />
          <span>Add new address</span>
        </Button>

        <div className="flex justify-end">
          <Button type="default" style={{ marginRight: '8px' }} danger onClick={() => handleOpen(false)}>
            Cancel
          </Button>
          <Button type="primary" danger onClick={() => handleChangeAddress()}>
            Confirm
          </Button>
        </div>
      </React.Fragment>
    );
  };

  const items: TabsProps['items'] = [
    {
      key: '1',
      label: null,
      children: <TabShowAddress />,
    },
    {
      key: '2',
      label: null,
      children: <TabCreateAddress onBack={() => setActiveTab({ currentTab: '1', addressId: -1 })} />,
    },
    {
      key: '3',
      label: null,
      children: <TabEditAddress addressId={activeTab.addressId} onBack={() => setActiveTab({ currentTab: '1', addressId: -1 })} />,
    },
  ];

  return (
    <Modal open={isOpen} onCancel={() => handleOpen(false)} footer={null}>
      <Tabs activeKey={activeTab.currentTab} items={items} defaultActiveKey="1" />
    </Modal>
  );
};

export default SelectAddress;
