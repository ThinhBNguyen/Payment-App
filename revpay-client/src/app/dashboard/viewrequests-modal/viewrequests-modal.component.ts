import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
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
        console.log('Request Accepted' + response) 
      },
      error:(error) => {
        console.error('Error accepting request' + error)
      }
    })
  }

  declineRequest(requestId: number): void {
    this.remoteService.declinePaymentRequest(requestId).subscribe({
      next:(response) => {
        console.log('Request Declined' + response)
      },
      error:(error) => { console.error('Error declining request' + error) 
    }
    })
  }

}
