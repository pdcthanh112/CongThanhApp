import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import ProductItemCard from '../ProductItemCard'


const product = {
    "userId": 1,
    "title": "Wave hello! 👋",
    "completed": false,
    "id": 1
}

const mockSetTodos = jest.fn()

describe('AddTodo', () => {

    describe('Render', () => {

        it('should render an article', () => {
            render(<ProductItemCard product={product} setTodos={mockSetTodos} />) 

            //ACT
            const article = screen.getByRole('article')

            expect(article).toBeInTheDocument()// ASSERT
        })

        it('should render a label', () => {
            render(<ProductItemCard product={mockTodo} setTodos={mockSetTodos} />) // ARRANGE

            //ACT
            const label = screen.getByTestId('todo-item')

            expect(label).toBeInTheDocument()// ASSERT
        })

        it('should render a checkbox', () => {
            render(<ProductItemCard product={mockTodo} setTodos={mockSetTodos} />) // ARRANGE

            //ACT
            const checkbox = screen.getByRole('checkbox')

            expect(checkbox).toBeInTheDocument()// ASSERT
        })

        it('should render a button', () => {
            render(<ProductItemCard product={mockTodo} setTodos={mockSetTodos} />) // ARRANGE

            //ACT
            const button = screen.getByRole('button')

            expect(button).toBeInTheDocument()// ASSERT
        })

    })

    describe('Behavior', () => {


        it('should call setTodos when checkbox clicked', async () => {
            render(<ProductItemCard todo={mockTodo} setTodos={mockSetTodos} />) // ARRANGE

            //ACT
            const checkbox = screen.getByRole('checkbox')
            await userEvent.click(checkbox)

            expect(mockSetTodos).toBeCalled()// ASSERT
        })

        it('should call setTodos when button clicked', async () => {
            render(<ProductItemCard todo={mockTodo} setTodos={mockSetTodos} />) // ARRANGE

            //ACT
            const button = screen.getByRole('button')
            await userEvent.click(button)

            expect(mockSetTodos).toBeCalled()// ASSERT
        })

    })
})