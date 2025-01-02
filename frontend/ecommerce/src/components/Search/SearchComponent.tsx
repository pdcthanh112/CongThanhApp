'use client';
import React, { useEffect, useRef, useState } from 'react';
import { Search as SearchIcon, ArrowDropDownOutlined } from '@mui/icons-material';
import { useTranslations } from 'next-intl';
import { Card } from '@mui/material';
import { useMutation, useQuery } from '@tanstack/react-query';
import { CATEGORY_KEY } from '@/utils/constants/queryKey';
import { getAllCategoryJson } from '@/api/categoryApi';
import { Category } from '@/models/types';
import { Input } from '@/components/ui';
import SearchModal from './SearchModal';
import { useAuthenticated } from '@/hooks/auth/useAuthenticated';
import { addSearchHistoryItem } from '@/api/searchApi';

export default function SearchComponent() {
  const [showModal, setShowModal] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const modalRef = useRef<HTMLDivElement | null>(null);

  const { user } = useAuthenticated();
  const t = useTranslations();

  const { data: categoryData, isLoading } = useQuery({
    queryKey: [CATEGORY_KEY],
    queryFn: async () => await getAllCategoryJson().then((response) => response.data),
  });

  const { mutate: addSearchHistory } = useMutation({
    mutationKey: ['search-history'],
    mutationFn: async (keyword: string) => await addSearchHistoryItem('user email', keyword),
  });

  const handleSearch = (term: string) => {
    if (!term.trim()) return;

    // Thêm term vào search history
    addSearchHistory(term);

    // Thực hiện search
    console.log('Searching for:', term);
    setSearchTerm(term);
    setShowModal(false);
  };

  useEffect(() => {
    const handleClickOutside = (event: any) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        setShowModal(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  return (
    <div className="hidden sm:flex items-center h-10 rounded-md flex-grow relative" ref={modalRef}>
      <span className="bg-gray-300 h-[2.5rem] w-20 rounded-l-md flex justify-center items-center relative group">
        <span className="">
          {t('common.all')}
          <ArrowDropDownOutlined />
        </span>

        <Card className="text-[#a4a4a4] text-sm hidden absolute transform translate-x-[36%] translate-y-[56%] py-2 w-[15rem] group-hover:block group-hover:z-50 max-h-96 group-hover:overflow-y-scroll">
          {categoryData?.map((item: Category) => (
            <div key={item.id} className="leading-6 px-2 hover:bg-gray-100">
              {item.name}
            </div>
          ))}
        </Card>
      </span>

      <Input
        className="p-2 h-full w-6 flex-grow flex-shrink focus:outline-none px-4 rounded-none"
        onFocus={() => setShowModal(true)}
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        onKeyDown={(event) => event.key === 'Enter' && handleSearch(searchTerm)}
      />

      {showModal && <SearchModal searchTerm={searchTerm} handleSearch={handleSearch} />}

      <SearchIcon
        className="!w-14 !h-10 p-1 bg-yellow-400 hover:bg-yellow-500 rounded-r-md hover:cursor-pointer"
        onClick={() => handleSearch(searchTerm)}
      />
    </div>
  );
}
