import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
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

  accounts: any[] = [];

  sendDetails = {
    recipientIdentifier:  '',
    amount: 0,
    senderAccountId: null,
  }

  constructor(private remoteService: RemoteService){}

  ngOnInit(): void {
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
      this.remoteService.sendMoney(this.sendDetails).subscribe(
        response => {
          console.log('Money sent successfully', response);
          // Add any additional logic after successful sending
        },
        error => {
          console.error('Error sending money', error);
        }
      );
    }
  
}
