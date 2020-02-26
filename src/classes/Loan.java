package classes;

import java.time.LocalDate;
import java.util.UUID;

public class Loan
{
    private String uid, title, description;
    private int amount, amortization;
    private double intrest;
    private LocalDate startDate, endDate;

    public Loan(String title, String description, double intrest, int amount, int amortization, LocalDate startDate,
		LocalDate endDate)
    {
	this.uid = UUID.randomUUID().toString();
	this.title = title;
	this.description = description;
	this.intrest = intrest;
	this.amount = amount;
	this.amortization = amortization;
	this.startDate = startDate;
	this.endDate = endDate;
    }

    public String getUid() {
	return uid;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public double getIntrest() {
	return intrest;
    }

    public int getAmount() {
	return amount;
    }

    public void setAmount(int amount) {
	this.amount = amount;
    }

    public LocalDate getStartDate() {
	return startDate;
    }

    public void setStartDate(LocalDate startDate) {
	this.startDate = startDate;
    }

    public LocalDate getEndDate() {
	return endDate;
    }

    public void setEndDate(LocalDate endDate) {
	this.endDate = endDate;
    }

    public int getAmortization() {
	return amortization;
    }

    public void setAmortization(final int amortization) {
	this.amortization = amortization;
    }

    public void setIntrest(final double intrest) {
	this.intrest = intrest;
    }



    @Override public String toString() {
	return "title="+title + ", description='" + description +", intrest='" +
	       intrest + ", startDate=" + startDate + ", endDate=" + endDate;
    }
}
