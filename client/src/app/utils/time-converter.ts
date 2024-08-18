export function formatTimeFromString(dateString: string): string {
  const dateArray = dateString.split(',').map(Number);

  if (dateArray.length === 5) {
    const [year, month, day, hour, minute] = dateArray;
    const date = new Date(Date.UTC(year, month - 1, day, hour, minute));

    const formattedHour = String(date.getUTCHours()).padStart(2, '0');
    const formattedMinute = String(date.getUTCMinutes()).padStart(2, '0');

    const timeString = `${formattedHour}:${formattedMinute}`;

    return timeString;
  }

  return '';
}
