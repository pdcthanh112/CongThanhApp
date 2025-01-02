import React from 'react';
import { getSearchHistory, removeSearchHistoryItem, getSearchTrending, getSearchRecommend } from '@/api/searchApi';
import { useMutation, useQuery } from '@tanstack/react-query';
import { Tag } from 'antd';
import { Clock, TrendingUp, X } from 'lucide-react';

const searchHistory = [
  { id: 1, value: 'abchasf ạldfs adsjlj' },
  { id: 2, value: 'jljalsdf lajslfj alsjdf' },
  { id: 3, value: 'lajlsjf' },
  { id: 4, value: 'aslfj ajsljf' },
  { id: 5, value: 'asljfls lasjlfjo iasfjll' },
];

const searchTrending = [
  'lajlfjasl ajfls alsjfl',
  'asjljas asjlsf',
  'aloijasdl aljlfdjl akjflda sdjdis',
  'ljlkjajldsjfjdslj',
];

type PropsType = {
  searchTerm: string;
  handleSearch: (term: string) => void;
};

export default function SearchModal({ searchTerm, handleSearch }: PropsType) {
  // const {data: searchHistory} = useQuery({queryKey: ['search-history'], queryFn: async () => await getSearchHistory()})
  // const {data: searchTrending} = useQuery({queryKey: ['search-trending'], queryFn: async () => await getSearchTrending()})

  const { mutate: removeSearchItem } = useMutation({
    mutationKey: ['search-history'],
    mutationFn: async (id: number) => await removeSearchHistoryItem(id),
  });

  const {} = useQuery({
    queryKey: ['search-recommend', searchTerm],
    queryFn: async () => await getSearchRecommend(searchTerm).then(response => response.data)
  })

  return (
    <div className="absolute top-full left-0 w-full mt-2 bg-white rounded-lg shadow-lg border border-gray-200 z-50">
      {searchTerm ? (
        <div>alsd;f;a</div>
      ) : (
        <React.Fragment>
          <div className="p-4 border-t border-gray-100">
            <div className="flex items-center gap-2 text-sm text-gray-500 mb-2">
              <TrendingUp className="w-4 h-4" />
              <span>Trending Searches</span>
            </div>
            {searchTrending.length > 0 &&
              searchTrending.map((term, id) => (
                <Tag
                  key={id}
                  icon="#"
                  className="py-1 px-2 hover:bg-gray-100 cursor-pointer rounded"
                  onClick={() => handleSearch(term)}
                >
                  {term}
                </Tag>
              ))}
          </div>

          <div className="p-4">
            <div className="flex items-center gap-2 text-sm text-gray-500 mb-2">
              <Clock className="w-4 h-4" />
              <span>Recent Searches</span>
            </div>
            {searchHistory.length > 0 &&
              searchHistory.map((term) => (
                <div
                  key={term.id}
                  className="flex items-center justify-between py-2 px-2 hover:bg-gray-100 cursor-pointer rounded"
                  onClick={() => handleSearch(term.value)}
                >
                  <span>{term.value}</span>
                  <X className="w-4 h-4 text-gray-400 hover:text-gray-600" onClick={(e) => removeSearchItem(term.id)} />
                </div>
              ))}
          </div>
        </React.Fragment>
      )}
    </div>
  );
}
