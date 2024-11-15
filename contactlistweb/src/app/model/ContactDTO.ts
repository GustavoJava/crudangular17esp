import { DatePipe } from "@angular/common";

export interface ContactDTO {
  id:        number;
  name:      string;
  email:     string;
  createdAt: Date;
}
