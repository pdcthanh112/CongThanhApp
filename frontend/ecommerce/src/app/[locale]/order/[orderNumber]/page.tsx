export default function OrderDetailPage({ params }: { params: { locale: string; orderNumber: string } }) {
  return (
    <div className="bg-white w-4/5 mx-auto p-2">
      <div className="min-h-[30rem]">
        <h1>Order Code: {params.orderNumber}</h1>
      </div>
    </div>
  );
}