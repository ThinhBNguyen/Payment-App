import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface UserDto {
  firstName: string
  lastName: string
  userName: string
  passWord: string
  email: string
  role: string
}

@Injectable({
  providedIn: 'root'
})
export class RemoteService {

  httpOptions = {
    observe: 'response', 
    headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })}
  private baseUrl = "http://localhost:8080";

  constructor(private http: HttpClient) { 
  }

  registerUser(user: UserDto): Observable<HttpResponse<Object>>{
    return this.http.post(this.baseUrl+"/save", JSON.stringify(user),{
      observe: 'response', 
      headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })});
  }

  login(username: string, password: string): Observable<any> {
    const headers = new HttpHeaders({ 
      Authorization: 'Basic ' + btoa(username + ':' + password) 
    });
    return this.http.post(this.baseUrl+'/login', {}, { headers: headers, withCredentials: true });
  }

  getUsers(): Observable<any> {
    return this.http.get(this.baseUrl + '/users', { withCredentials: true });
  }
}


