export function parseAndFormatDate(dateString: string): string {
  const parts = dateString.split(',');
  const date = new Date(
    parseInt(parts[0], 10),
    parseInt(parts[1], 10) - 1,
    parseInt(parts[2], 10),
    parseInt(parts[3], 10),
    parseInt(parts[4], 10),
    parseInt(parts[5], 10)
  );
  return date.toLocaleDateString();
}
