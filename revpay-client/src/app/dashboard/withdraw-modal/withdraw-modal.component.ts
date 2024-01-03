import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RemoteService } from '../../remote.service';
import { DashboardComponent } from '../dashboard.component';

@Component({
  selector: 'app-withdraw-modal',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './withdraw-modal.component.html',
  styleUrl: './withdraw-modal.component.css'
})
export class WithdrawModalComponent {

  @Input() accountId: any;

  amount : number = 0;

  constructor(private remoteService:RemoteService,
              private dashBoard:DashboardComponent){}

  onSubmit() {
    console.log('accountid: ' + this.accountId)
    this.remoteService.withdraw(this.accountId, this.amount).subscribe({
      next:(balance)=>{
        console.log("deposit successful" + balance)
        this.dashBoard.onChange(balance,this.accountId)
      },
      error: (error)=>{
        console.log("deposit failed" + error)
      }
    })
  }

  preventNegative(event: KeyboardEvent) {
    if (event.key === '-' || event.key === '+') {
      event.preventDefault();
    }
  }

}
