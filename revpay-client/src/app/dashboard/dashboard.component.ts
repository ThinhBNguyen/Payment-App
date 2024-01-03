import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { Router, RouterLink } from '@angular/router';
import { Card, RemoteService } from '../remote.service';
import { CardModalComponent } from './card-modal/card-modal.component';
import { DepositModalComponent } from './deposit-modal/deposit-modal.component';
import { SendModalComponent } from './send-modal/send-modal.component';
import { TransactionModalComponent } from './transaction-modal/transaction-modal.component';
import { WithdrawModalComponent } from './withdraw-modal/withdraw-modal.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [FormsModule, RouterLink, CommonModule,
    CardModalComponent,
    DepositModalComponent,
    SendModalComponent,
    TransactionModalComponent,
    WithdrawModalComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  accounts: any[] = [];

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
          this.router.navigate(['']);
      },
          error: (error) => {
              this.router.navigate(['']);
          }
      });
    }


    onSubmit() {
      this.remoteService.createAccount(this.newAccount).subscribe({
        next:(account) => {
          console.log('Creating account success')
          this.accounts.push(account);
        },
        error: (error) => {
          console.log('Failed creating account', error)
          console.log(this.newAccount)
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
}
