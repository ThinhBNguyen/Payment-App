import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Output, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RemoteService } from '../../remote.service';

@Component({
  selector: 'app-viewrequests-modal',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './viewrequests-modal.component.html',
  styleUrl: './viewrequests-modal.component.css'
})
export class ViewrequestsModalComponent {
  @ViewChild('closeModal') closeModal!: ElementRef;
  @Output() succcessMessage = new EventEmitter<string>();
  @Output() failureMessage = new EventEmitter<string>();
  paymentRequests :any[] = [];

  constructor(private remoteService: RemoteService){}

  ngOnInit():void{
    this.remoteService.getReceivedPaymentRequests().subscribe(
      (requests) => this.paymentRequests = requests,
      (error) => console.error('Error fetching payment requests', error)
    );
  }

  acceptRequest(requestId: number): void {
    this.remoteService.acceptPaymentRequest(requestId).subscribe({
      next:(response) => {
        this.succcessMessage.emit('Request Accepted');
        this.closeModal.nativeElement.click();
      },
      error:(error) => {
        this.failureMessage.emit('Error accepting request');
        this.closeModal.nativeElement.click();
      }
    })
  }

  declineRequest(requestId: number): void {
    this.remoteService.declinePaymentRequest(requestId).subscribe({
      next:(response) => {
        this.succcessMessage.emit('Request Declined');
        this.closeModal.nativeElement.click();
      },
      error:(error) => { 
        this.failureMessage.emit('Error Declining request');
        this.closeModal.nativeElement.click();
    }
    })
  }

}
