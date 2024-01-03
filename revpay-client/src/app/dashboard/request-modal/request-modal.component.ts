import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RemoteService } from '../../remote.service';

@Component({
  selector: 'app-request-modal',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './request-modal.component.html',
  styleUrl: './request-modal.component.css'
})
export class RequestModalComponent {
  @ViewChild('closeModal') closeModal!: ElementRef;
  @Output() succcessMessage = new EventEmitter<string>();
  @Output() failureMessage = new EventEmitter<string>();
accounts: any[] = [];

@Input() accountId: any;

  paymentRequestDetails = {
    senderAccountId: null,
    receiverAccountId: null,
    amount: 0
  }

  constructor(private remoteService: RemoteService){}

  ngOnInit(): void{
    this.remoteService.getAccountsWithCard().subscribe(
      data =>  {

        console.log('got accounts with card' + data)
        this.accounts = data;
      },
      error => {
        console.error('Error fetching accounts', error);
      }
    );
  }


  onSubmit() {
    console.log("Sending payment request details: ", this.paymentRequestDetails);
      this.remoteService.submitPaymentRequest(this.paymentRequestDetails).subscribe({
      next:(response) => {
        this.succcessMessage.emit('Request sent');
        this.closeModal.nativeElement.click();
       },
        error:(error) => {
          this.failureMessage.emit('Sending request failed');
          this.closeModal.nativeElement.click();
      }
    })
  }

}
