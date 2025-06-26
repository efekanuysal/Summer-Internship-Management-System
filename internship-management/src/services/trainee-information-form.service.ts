import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Announcement} from "./announcement.service";
import {response} from "express";


export interface InitialTraineeInformationForm {
  type: string;
  code: string;
  semester: string;
  health_insurance: boolean;
  fill_user_name: string;
  company_user_name: string;
  branch_name: string;
  company_branch_address: string;
  company_branch_phone: string;
  company_branch_district: string;
  company_branch_country: string;
  company_branch_city: string;
  company_branch_email: string;
  position: string;
  startDate: string;
  endDate: string;
}

@Injectable({
  providedIn: 'root'
})
export class TraineeInformationFormService{
  private apiUrl = '';

  constructor(private http: HttpClient) {}



  getStudentTraineeForms(userName: string): Observable<any> {
    this.apiUrl= "http://localhost:8080/api/traineeFormStudent"
    return this.http.post<any>(this.apiUrl, userName);

  }

  getCoordinatorTraineeForms() {
    this.apiUrl= "http://localhost:8080/api/traineeFormCoordinator"
    return this.http.get<any>(this.apiUrl);
  }

  getInstructorTraineeForms(userName: string) {
    this.apiUrl= "http://localhost:8080/api/traineeFormInstructor"
    return this.http.post<any>(this.apiUrl, userName);
  }

  getCompanyTraineeForms(userName: string) {
    this.apiUrl = `http://localhost:8080/api/company-branch/approvedInternships/${userName}`
    return this.http.get<any>(this.apiUrl);
  }

  getCompanies(){
    this.apiUrl = "http://localhost:8080/api/company/all"
    return this.http.get<any>(this.apiUrl);
  }

  getCompanyBranches(userName: string){
    this.apiUrl = "http://localhost:8080/api/company-branch"
    return this.http.post<any>(this.apiUrl, userName);
  }

  addNewStudentTraineeInformationForm(newForm: InitialTraineeInformationForm){
    this.apiUrl = "http://localhost:8080/api/initialTraineeInformationForm"
    return this.http.post<any>(this.apiUrl, newForm);
  }

  editStudentTraineeInformationForm(newForm: InitialTraineeInformationForm, id:number){
    this.apiUrl = "http://localhost:8080/api/initialTraineeInformationForm/".concat(String(id))
    return this.http.put<any>(this.apiUrl, newForm);
  }

  deleteStudentTraineeInformationForm2(username: string, id: number) {
    this.apiUrl = `http://localhost:8080/api/traineeFormStudent/approved/${id}?username=${encodeURIComponent(username)}`;
    return this.http.delete<any>(this.apiUrl);
  }

  deleteStudentTraineeInformationForm(username: string, id: number) {
    this.apiUrl = `http://localhost:8080/api/traineeFormStudent/initial/${id}?username=${encodeURIComponent(username)}`;
    return this.http.delete<any>(this.apiUrl);
  }

  coordinatorApproveStudentTraineeInformationForm(username: string, id:number){
    console.log(id,username);
    this.apiUrl = `http://localhost:8080/api/coordinatorApprove/approve/${id}?username=${encodeURIComponent(username)}`;
    return this.http.post<any>(this.apiUrl, {});
  }

  coordinatorRejectStudentTraineeInformationForm(username: string, endDate: string, position: string) {
    this.apiUrl = `http://localhost:8080/api/coordinatorApprove/reject`;
    const payload = {
      username: username,
      endDate: endDate,
      position: position
    };
    return this.http.post<any>(this.apiUrl, payload);
  }


}
