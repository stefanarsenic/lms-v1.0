export function formatDateFromString(dateString: string): string {
  const dateArray = dateString.split(',').map(Number);

  if (dateArray.length === 6) {
    const [year, month, day, hour, minute, seconds] = dateArray;
    const date = new Date(Date.UTC(year, month - 1, day, hour, minute, seconds));

    const formattedYear = date.getUTCFullYear();
    const formattedMonth = String(date.getUTCMonth() + 1).padStart(2, '0');
    const formattedDay = String(date.getUTCDate()).padStart(2, '0');
    const formattedHour = String(date.getUTCHours()).padStart(2, '0');
    const formattedMinute = String(date.getUTCMinutes()).padStart(2, '0');
    const formattedSecond = '00';

    const isoDateString = `${formattedYear}-${formattedMonth}-${formattedDay}T${formattedHour}:${formattedMinute}:${formattedSecond}Z`;

    return isoDateString;
  }
  if (dateArray.length === 5) {
    const [year, month, day, hour, minute] = dateArray;
    const date = new Date(Date.UTC(year, month - 1, day, hour, minute));

    const formattedYear = date.getUTCFullYear();
    const formattedMonth = String(date.getUTCMonth() + 1).padStart(2, '0');
    const formattedDay = String(date.getUTCDate()).padStart(2, '0');
    const formattedHour = String(date.getUTCHours()).padStart(2, '0');
    const formattedMinute = String(date.getUTCMinutes()).padStart(2, '0');
    const formattedSecond = '00';

    const isoDateString = `${formattedYear}-${formattedMonth}-${formattedDay}T${formattedHour}:${formattedMinute}:${formattedSecond}Z`;

    return isoDateString;
  }
  else if(dateArray.length === 3){
    const [year, month, day] = dateArray;
    const date = new Date(Date.UTC(year, month - 1, day));

    const formattedYear = date.getUTCFullYear();
    const formattedMonth = String(date.getUTCMonth() + 1).padStart(2, '0');
    const formattedDay = String(date.getUTCDate()).padStart(2, '0');

    const isoDateString = `${formattedYear}-${formattedMonth}-${formattedDay}`;

    return isoDateString;
  }
  throw new Error('Invalid date string format. Expected a string with 5 or 3 comma-separated numbers.');
}
