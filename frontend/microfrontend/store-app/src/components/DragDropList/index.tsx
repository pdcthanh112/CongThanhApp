'use client';

import React, { useState } from 'react';

type DragDropListProps = {
  items: string[];
  onReorder: (newItems: string[]) => void;
};

const DragDropList: React.FC<DragDropListProps> = ({ items, onReorder }) => {
  const [draggingIndex, setDraggingIndex] = useState<number | null>(null);

  const handleDragStart = (index: number) => {
    setDraggingIndex(index);
  };

  const handleDragOver = (index: number) => {
    if (draggingIndex === null || draggingIndex === index) return;

    const newItems = [...items];
    const draggedItem = newItems.splice(draggingIndex, 1)[0];
    newItems.splice(index, 0, draggedItem);

    setDraggingIndex(index);
    onReorder(newItems);
  };

  const handleDragEnd = () => {
    setDraggingIndex(null);
  };

  return (
    <div className="w-96 mx-auto mt-5">
      {items.map((item, index) => (
        <div
          key={index}
          draggable
          onDragStart={() => handleDragStart(index)}
          onDragOver={(e) => {
            e.preventDefault();
            handleDragOver(index);
          }}
          onDragEnd={handleDragEnd}
          className="border border-dashed p-2 mb-2 bg-white shadow-md cursor-move"
        >
          {item}
        </div>
      ))}
    </div>
  );
};

export default DragDropList;
