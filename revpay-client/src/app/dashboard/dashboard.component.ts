import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { Router, RouterLink } from '@angular/router';
import { Card, RemoteService } from '../remote.service';
import { CardModalComponent } from './card-modal/card-modal.component';
import { DepositModalComponent } from './deposit-modal/deposit-modal.component';
import { RequestModalComponent } from './request-modal/request-modal.component';
import { SendModalComponent } from './send-modal/send-modal.component';
import { TransactionModalComponent } from './transaction-modal/transaction-modal.component';
import { ViewrequestsModalComponent } from './viewrequests-modal/viewrequests-modal.component';
import { WithdrawModalComponent } from './withdraw-modal/withdraw-modal.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [FormsModule, RouterLink, CommonModule,
    CardModalComponent,
    DepositModalComponent,
    SendModalComponent,
    TransactionModalComponent,
    WithdrawModalComponent,
    ViewrequestsModalComponent,
    RequestModalComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  @ViewChild('closeModal') closeModal!: ElementRef;
  accounts: any[] = [];

  successMessage = '';
  failureMessage = '';

  newAccount = {
    type: ''
  }

  selectedAccountId: any;
  
;
  constructor(private remoteService: RemoteService,
    private router: Router) {}

    ngOnInit(): void {
        this.remoteService.getUserAccounts().subscribe(
            data => this.accounts = data,
            error => console.error('Error fetching accounts')
        );
    }

    logout() {
      this.remoteService.logout().subscribe({
        next: (response) => {
          this.remoteService.setLoggedIn(false);
          this.router.navigate(['']);
      },
      error: (error) => {
        this.remoteService.setLoggedIn(false);
        this.router.navigate(['']);
      }
      });
    }


    onSubmit() {
      this.remoteService.createAccount(this.newAccount).subscribe({
        next:(account) => {
          this.accounts.push(account);
          this.handleSuccess('Account created')
          this.closeModal.nativeElement.click();
        },
        error: (error) => {
          this.handleError('Failed creating account')
          this.closeModal.nativeElement.click();
        }
      })
    }

    select(accountId: number){
      this.selectedAccountId = accountId;
    }

    onCardAdded(card: Card, accountId: number) {
      const account = this.accounts.find(acc => acc.id === accountId);
      if(account){
        account.card = card;
      }
    }

    onChange(amount : number, accountId: number){
      const account = this.accounts.find(acc => acc.id === accountId);
      if(account){
        account.balance = amount;
      }
    }


    handleSuccess(message: string) {
      this.successMessage = message;
      setTimeout(() => this.successMessage = '', 3000);
    }
  
    handleError(message: string) {
      this.failureMessage = message;
      setTimeout(() => this.failureMessage = '', 3000);
    }
}
