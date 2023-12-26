import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface UserDto {
  firstName: string
  lastName: string
  userName: string
  password: string
  email: string
}

@Injectable({
  providedIn: 'root'
})
export class RemoteService {

  private baseUrl = "http://localhost:8080";
  private httpOptions = {
    observe: 'response',
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { 
  }

  registerUser(user: UserDto): Observable<any>{
    return this.http.post(`${this.baseUrl}/save`, JSON.stringify(user));
  }
}


