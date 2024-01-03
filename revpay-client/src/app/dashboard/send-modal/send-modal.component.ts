import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Output, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RemoteService } from '../../remote.service';

@Component({
  selector: 'app-send-modal',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './send-modal.component.html',
  styleUrl: './send-modal.component.css'
})
export class SendModalComponent {
  @ViewChild('closeModal') closeModal!: ElementRef;
  @Output() succcessMessage = new EventEmitter<string>();
  @Output() failureMessage = new EventEmitter<string>();
  accounts: any[] = [];

  sendDetails = {
    recipientIdentifier:  '',
    amount: 0,
    senderAccountId: null,
  }

  constructor(private remoteService: RemoteService){}

  ngOnInit(): void {
    this.remoteService.getAccountsWithCard().subscribe(
      data =>  { this.accounts = data;},);
  }


  onSubmit() {
      this.remoteService.sendMoney(this.sendDetails).subscribe(
        response => {
          this.succcessMessage.emit('Funds transferred');
          this.closeModal.nativeElement.click();
        },
        error => {
          this.failureMessage.emit('Transfer failed');
          this.closeModal.nativeElement.click();
        }
      );
    }
  
}
