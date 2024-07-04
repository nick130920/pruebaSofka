import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../constants/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _apiUrl: string = `${environment.apiUrl}/auth`;

  constructor(private _http: HttpClient) { }

  login(username: string): Observable<any> {
    return this._http.post(`${this._apiUrl}/login`, {}, { params: { username } });
  }

  logout(username: string): Observable<any> {
    return this._http.post(`${this._apiUrl}/logout`, {}, { params: { username } });
  }
}
