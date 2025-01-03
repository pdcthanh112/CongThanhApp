export const slugGenerator = (name: string) => {
  const slug =
    name &&
    name
      .toString()
      .toLowerCase()
      .normalize("NFKD")
      .replace(/[\u0300-\u036f]/g, "")
      .replace(/\s*\/\s*/g, "-")
      .replace(/\s*&amp;\s*/g, "-and-")
      .replace(/\s+/g, "-")
      .replace(/apos;/g, "")
      .replace(/_/g, "")
      .replace(/[^\w-]+/g, "");
  return slug === "" ? "unknown" : slug;
};
