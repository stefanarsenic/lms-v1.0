import {jwtDecode} from "jwt-decode";

export function isTokenExpired(token: string): boolean {
  if (!token) {
    return true;
  }

  try {
    const decoded: any = jwtDecode(token);
    const exp = decoded.exp;
    return Date.now() >= exp * 1000;
  } catch (error) {
    console.error("Error decoding token:", error);
    return true;
  }
}
