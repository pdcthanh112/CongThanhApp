import { render, screen } from '@testing-library/react'
import ShowListProduct from '../ShowListProduct'

const dummyData = [
    {
      id: 'lkaslfjldsfjaslks',
      name: 'AAAAAAAAAAAAA',
      category: 1412423,
      slug: 'adfasddf',
      description: 'adladsjljladsf',
      supplier: 'string',
      image: [
        {
          id: 1,
          product: 'lkaslfjldsfjaslks',
          imagePath: 'https://images.stockcake.com/public/7/3/7/7375ee9e-ecee-484f-9f33-7a9a55d079ce_large/sunny-daisy-field-stockcake.jpg',
          alt: 'lkaslfjldsfjaslks',
          isDefault: true,
        },
        {
          id: 2,
          product: 'lkaslfjldsfjaslks',
          imagePath: 'https://images.stockcake.com/public/7/3/7/7375ee9e-ecee-484f-9f33-7a9a55d079ce_large/sunny-daisy-field-stockcake.jpg',
          alt: 'lkaslfjldsfjaslks',
          isDefault: false,
        },
        {
          id: 3,
          product: 'lkaslfjldsfjaslks',
          imagePath: 'https://images.stockcake.com/public/7/3/7/7375ee9e-ecee-484f-9f33-7a9a55d079ce_large/sunny-daisy-field-stockcake.jpg',
          alt: 'lkaslfjldsfjaslks',
          isDefault: false,
        },
      ],
      brand: 'string',
      status: 'string',
      variant: null,
      attribute: null,
    }
  ]

const mockSetTodos = jest.fn()

describe('ShowListProduct', () => {

    it('should render "No Todos Available" when the array is empty', () => {
        render(<ShowListProduct listProduct={[]} loading={false} />) 

        const message = screen.getByText('No Product Available')

        expect(message).toBeInTheDocument()
    })

    it('should render a list with the correct number of items', () => {
        render(
            <ShowListProduct listProduct={dummyData} setTodos={mockSetTodos} loading={false}/>
        ) // ARRANGE

        //ACT
        const todosArray = screen.getAllByRole('article')

        expect(todosArray.length).toBe(2)// ASSERT
    })

    it('should render the todos in the correct order', () => {
        render(
            <ShowListProduct listProduct={dummyData} loading={false} />
        ) // ARRANGE

        //ACT
        const firstItem = screen.getAllByTestId("todo-item")[0]

        expect(firstItem).toHaveTextContent("Get Coffee ☕☕☕")// ASSERT
    })

})