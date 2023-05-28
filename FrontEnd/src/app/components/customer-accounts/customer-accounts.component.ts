import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Customer} from "../../models/customer.model";
import {CustomerService} from "../../services/customer.service";

@Component({
  selector: 'app-customer-accounts',
  templateUrl: './customer-accounts.component.html',
  styleUrls: ['./customer-accounts.component.css']
})
export class CustomerAccountsComponent implements OnInit {
  customerId!: string;
  RO:Boolean | undefined;
  customer!: Customer;
  roles: string | undefined;

  customer1!: Customer;
  accounts: any = [];
  errorMessage: any;

  constructor(private route: ActivatedRoute, private router: Router,private customerService: CustomerService) {
    this.customerId = this.route.snapshot.params['id'];
    this.customer1 = this.router.getCurrentNavigation()?.extras.state as Customer;
    this.customer = this.router.getCurrentNavigation()?.extras.state as Customer;
    this.customerService.getAccountsOfCustomer(parseInt(this.customerId)).subscribe({
      next: (data: any) => {
        console.log(data);
        this.accounts = data;
      },
      error: (err: any) => {
        console.log(err);
      }
    });

    this.customerService.getOneCustomer(parseInt(this.customerId)).subscribe({
      next: (data: any) => {
        this.customer = data;
        this.customer1 = data;
      },
      error: (err: any) => {
        console.log(err);
      }
    });
  }

  ngOnInit(): void {
    // @ts-ignore
    this.roles = localStorage.getItem("ROLES");
    // @ts-ignore
    if(this.roles.includes("ADMIN"))
    {
      console.log(this.customer1)
      this.customer1=this.customer;
    }else
    {
      // @ts-ignore
      this.customerService.getIdCustomerByname(localStorage.getItem("username")).subscribe({
        next: (data: any) => {
          this.customer1 =data;
        },
        error: (err: any) => {
          console.log(err);
        }
      });
    }

  }

  viewOperations(account: any) {
    this.router.navigateByUrl("/one-account/" + account.id);
  }
}
