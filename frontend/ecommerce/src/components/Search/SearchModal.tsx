import { getSearchHistory, removeSearchHistoryItem, getSearchTrending } from '@/api/searchApi';
import { Card } from '@/components/ui';
import { Clear } from '@mui/icons-material';
import { useMutation, useQuery } from '@tanstack/react-query';
import { Tag } from 'antd';

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

export default function SearchModal() {
  // const {data: searchHistory} = useQuery({queryKey: ['search-history'], queryFn: async () => await getSearchHistory()})
  // const {data: searchTrending} = useQuery({queryKey: ['search-trending'], queryFn: async () => await getSearchTrending()})

  const { mutate: removeSearchItem } = useMutation({
    mutationKey: ['search-history'],
    mutationFn: async (id: number) => await removeSearchHistoryItem(id),
  });

  return (
    <Card className="absolute top-12 w-[54rem] h-80 px-3 py-2">
      <div>
        {searchTrending.map((item) => (
          <Tag icon="#" className="px-2 py-1">
            {item}
          </Tag>
        ))}
      </div>
      {searchHistory.map((item) => (
        <div className="flex justify-between hover:bg-gray-200 py-2">
          <span className="hover:cursor-pointer">{item.value}</span>
          <Clear fontSize="small" className="opacity-70" onClick={() => removeSearchItem(item.id)} />
        </div>
      ))}
    </Card>
  );
}
