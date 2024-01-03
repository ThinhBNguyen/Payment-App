import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
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
  @ViewChild('closeModal') closeModal!: ElementRef;
  @Output() succcessMessage = new EventEmitter<string>();
  @Output() failureMessage = new EventEmitter<string>();
  @Input() accountId: any;

  amount : number = 0;

  constructor(private remoteService:RemoteService,
              private dashBoard:DashboardComponent){}

  onSubmit() {
    console.log('accountid: ' + this.accountId)
    this.remoteService.withdraw(this.accountId, this.amount).subscribe({
      next:(balance)=>{
        this.dashBoard.onChange(balance,this.accountId)
        this.succcessMessage.emit('Withdraw Success');
        this.closeModal.nativeElement.click();
      },
      error: (error)=>{
        this.failureMessage.emit('Withdraw Failed');
        this.closeModal.nativeElement.click();
      }
    })
  }

  preventNegative(event: KeyboardEvent) {
    if (event.key === '-' || event.key === '+') {
      event.preventDefault();
    }
  }

}
