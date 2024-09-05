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

  // Format the date to "YYYY-MM-DD"
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-based
  const day = String(date.getDate()).padStart(2, '0');

  return `${day}-${month}-${year}`;
}
