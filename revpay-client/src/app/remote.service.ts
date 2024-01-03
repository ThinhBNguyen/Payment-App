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

export interface Account {
  type: string
}

export interface AccountCard{
  card: {
    cardNumber: string;
    cardSecurity: string;
    type: string;
  };
}

export interface Card {
  cardNumber: string;
  cardSecurity: string;
  type: string;
}

export interface SendDetails{
  recipientIdentifier: string;
  senderAccountId: number | null;
  amount: number
}

export interface PaymentRequestDetails{
  senderAccountId: number | null,
    receiverAccountId: number | null,
    amount: number
}

@Injectable({
  providedIn: 'root'
})
export class RemoteService {
  getUserAccounts(): Observable<any> {
    return this.http.get(this.baseUrl + '/accounts/my-accounts', { withCredentials: true });
  }

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

  logout(): Observable<any> {
    return this.http.post(this.baseUrl+'/logout', {}, { withCredentials: true });
  }

  createAccount(account: Account): Observable<any> {
    return this.http.post(this.baseUrl+'/accounts/create-account',account,{ withCredentials: true })
  }

  addCard(card: Card, accountId: any) :Observable<any>{
    return this.http.post(this.baseUrl+`/accounts/${accountId}/add-card`,card,{ withCredentials: true })
  }

  getCard(card: Card, accountId: number): Observable<any> {
    return this.http.post(this.baseUrl+`/accounts/${accountId}/get-card`, card, { withCredentials: true });
  }

  deposit(accountId : number, amount : number) : Observable<any>{
    return this.http.post(this.baseUrl + `/accounts/${accountId}/deposit`, amount,{ withCredentials: true })
  }

  getAccountsWithCard(): Observable<any>{
    return this.http.get(this.baseUrl + '/accounts/accounts-with-card',{ withCredentials: true })
  }

  sendMoney(sendDetails: SendDetails): Observable<any>{
    return this.http.post(this.baseUrl + '/transactions/transfer',sendDetails,{ withCredentials: true })
  }

  getTransactions() : Observable<any> {
    return this.http.get(this.baseUrl + '/transactions/get-transactions', { withCredentials: true });
  }

  withdraw(accountId : number, amount : number) : Observable<any>{
    return this.http.post(this.baseUrl + `/accounts/${accountId}/withdraw`, amount,{ withCredentials: true })
  }

  getReceivedPaymentRequests(): Observable<any> {
    return this.http.get(this.baseUrl + '/payment/received-requests', { withCredentials: true });
  }
  
  acceptPaymentRequest(requestId: number): Observable<any> {
    return this.http.post(this.baseUrl + `/payment/accept-payment/${requestId}`, {}, { withCredentials: true });
  }
  
  declinePaymentRequest(requestId: number): Observable<any> {
    return this.http.post(this.baseUrl + `/payment/decline-payment/${requestId}`, {}, { withCredentials: true });
  }

  submitPaymentRequest(paymentRequestDetails: PaymentRequestDetails): Observable<any>{
    return this.http.post(this.baseUrl + `/payment/send-payment`, paymentRequestDetails, { withCredentials: true });
  }

}


