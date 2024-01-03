import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
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
  @ViewChild('closeModal') closeModal!: ElementRef;
  @Output() succcessMessage = new EventEmitter<string>();
  @Output() failureMessage = new EventEmitter<string>();
  @Input() accountId: any;

  amount : number = 0;

  constructor(private remoteService:RemoteService,
              private dashBoard:DashboardComponent){}

  onSubmit() {
    console.log('accountid: ' + this.accountId)
    this.remoteService.deposit(this.accountId, this.amount).subscribe({
      next:(balance)=>{
        this.dashBoard.onChange(balance,this.accountId)
        this.succcessMessage.emit('Deposit Success');
        this.closeModal.nativeElement.click();
      },
      error: (error)=>{
        this.failureMessage.emit('Deposit Failed');
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
