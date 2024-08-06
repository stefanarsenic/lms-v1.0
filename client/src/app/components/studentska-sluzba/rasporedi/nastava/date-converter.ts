export function formatDateFromString(dateString: string): string {
  // Parse the date string into an array of numbers
  const dateArray = dateString.split(',').map(Number);

  if (dateArray.length !== 5) {
    throw new Error('Invalid date string format. Expected a string with 5 comma-separated numbers.');
  }

  const [year, month, day, hour, minute] = dateArray;
  // Create Date object with month adjusted (0-based index)
  const date = new Date(year, month - 1, day, hour, minute);

  // Extract components
  const formattedYear = date.getFullYear();
  const formattedMonth = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-based
  const formattedDay = String(date.getDate()).padStart(2, '0');
  const formattedHour = String(date.getHours()).padStart(2, '0');
  const formattedMinute = String(date.getMinutes()).padStart(2, '0');
  const formattedSecond = '00'; // Seconds are not provided in the string

  // Construct the ISO 8601 string with time zone offset +02:00
  const isoDateString = `${formattedYear}-${formattedMonth}-${formattedDay}T${formattedHour}:${formattedMinute}:${formattedSecond}+02:00`;

  return isoDateString;
}
