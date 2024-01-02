import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RemoteService } from '../../remote.service';
import { DashboardComponent } from '../dashboard.component';

@Component({
  selector: 'app-deposit-modal',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './deposit-modal.component.html',
  styleUrl: './deposit-modal.component.css'
})
export class DepositModalComponent {

  @Input() accountId: any;

  amount : number = 0;

  constructor(private remoteService:RemoteService,
              private dashBoard:DashboardComponent){}

  onSubmit() {
    console.log('accountid: ' + this.accountId)
    this.remoteService.deposit(this.accountId, this.amount).subscribe({
      next:(balance)=>{
        console.log("deposit successful" + balance)
        this.dashBoard.onDeposit(balance,this.accountId)
      },
      error: (error)=>{
        console.log("deposit failed" + error)
      }
    })
  }
}
