import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CustomerDTO } from 'src/app/models/customer-dto';

@Component({
  selector: 'app-customer-card',
  templateUrl: './customer-card.component.html',
  styleUrls: ['./customer-card.component.scss']
})
export class CustomerCardComponent {

  @Input()
  customer: CustomerDTO = {}

  @Input()
  customerIndex: number = 0

  @Output()
  delete: EventEmitter<CustomerDTO> = new EventEmitter<CustomerDTO>()

  @Output()
  update: EventEmitter<CustomerDTO> = new EventEmitter<CustomerDTO>()

  constructor(){}

  get  customerImage(): string {
    const gender = this.customer.gender === 'MALE' ? 'men' : 'women'
    return `https://randomuser.me/api/portraits/${gender}/${this.customerIndex}.jpg`
  }

  onDelete():void{
    this.delete.emit(this.customer)
  }

  onUpdate():void{
    this.update.emit(this.customer)
  }

}
