 
import { Stack, Pagination as PaginationComponent } from '@mui/material';

const Pagination: React.FC<any> = ({ onChange, ...props }) => {
  return (
    <Stack spacing={2} className='flex items-center my-3'>
      <PaginationComponent 
        variant="outlined"
        shape="rounded"
        {...props}
        onChange={onChange}
      />
    </Stack>
  );
};

export default Pagination;
