import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FlashDealComponentsPage, FlashDealDeleteDialog, FlashDealUpdatePage } from './flash-deal.page-object';

const expect = chai.expect;

describe('FlashDeal e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let flashDealComponentsPage: FlashDealComponentsPage;
  let flashDealUpdatePage: FlashDealUpdatePage;
  let flashDealDeleteDialog: FlashDealDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FlashDeals', async () => {
    await navBarPage.goToEntity('flash-deal');
    flashDealComponentsPage = new FlashDealComponentsPage();
    await browser.wait(ec.visibilityOf(flashDealComponentsPage.title), 5000);
    expect(await flashDealComponentsPage.getTitle()).to.eq('Flash Deals');
    await browser.wait(ec.or(ec.visibilityOf(flashDealComponentsPage.entities), ec.visibilityOf(flashDealComponentsPage.noResult)), 1000);
  });

  it('should load create FlashDeal page', async () => {
    await flashDealComponentsPage.clickOnCreateButton();
    flashDealUpdatePage = new FlashDealUpdatePage();
    expect(await flashDealUpdatePage.getPageTitle()).to.eq('Create or edit a Flash Deal');
    await flashDealUpdatePage.cancel();
  });

  it('should create and save FlashDeals', async () => {
    const nbButtonsBeforeCreate = await flashDealComponentsPage.countDeleteButtons();

    await flashDealComponentsPage.clickOnCreateButton();

    await promise.all([
      flashDealUpdatePage.typeSelectLastOption(),
      flashDealUpdatePage.setTitleInput('title'),
      flashDealUpdatePage.setDescriptionInput('description'),
      flashDealUpdatePage.setImageUrlInput('imageUrl'),
      flashDealUpdatePage.setDealUrlInput('dealUrl'),
      flashDealUpdatePage.setPostedByInput('postedBy'),
      flashDealUpdatePage.setPostedDateInput('postedDate'),
      flashDealUpdatePage.setStartDateInput('startDate'),
      flashDealUpdatePage.setEndDateInput('endDate'),
      flashDealUpdatePage.setOriginalPriceInput('originalPrice'),
      flashDealUpdatePage.setCurrentPriceInput('currentPrice'),
      flashDealUpdatePage.setDiscountInput('discount'),
      flashDealUpdatePage.setActiveInput('active'),
      flashDealUpdatePage.positionSelectLastOption(),
      flashDealUpdatePage.getApprovedInput().click(),
      flashDealUpdatePage.setCountryInput('country'),
      flashDealUpdatePage.setCityInput('city'),
      flashDealUpdatePage.setPinCodeInput('pinCode'),
      flashDealUpdatePage.merchantDetailsSelectLastOption(),
      flashDealUpdatePage.dealCategorySelectLastOption(),
    ]);

    await flashDealUpdatePage.save();
    expect(await flashDealUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await flashDealComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last FlashDeal', async () => {
    const nbButtonsBeforeDelete = await flashDealComponentsPage.countDeleteButtons();
    await flashDealComponentsPage.clickOnLastDeleteButton();

    flashDealDeleteDialog = new FlashDealDeleteDialog();
    expect(await flashDealDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Flash Deal?');
    await flashDealDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(flashDealComponentsPage.title), 5000);

    expect(await flashDealComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
