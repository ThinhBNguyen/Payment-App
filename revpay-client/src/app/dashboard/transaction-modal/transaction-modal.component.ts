import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RemoteService } from '../../remote.service';

@Component({
  selector: 'app-transaction-modal',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './transaction-modal.component.html',
  styleUrl: './transaction-modal.component.css'
})
export class TransactionModalComponent {

  transactions :any[] = []

  constructor(private remoteService: RemoteService){}

  ngOnInit (): void {
    this.remoteService.getTransactions().subscribe(
      data => this.transactions = data,
      error => console.error('Error fetching transactions')
  );
  }

}
