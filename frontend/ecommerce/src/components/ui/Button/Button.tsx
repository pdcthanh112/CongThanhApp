const Button: React.FC<any> = ({ className, children, onClick, ...props }) => {
  
  return (
    <button className={`rounded-sm px-4 py-2 flex justify-center bg-yellow-400 ${className}`} onClick={onClick} {...props}>
      {children}
    </button>
  );
};

export default Button;
