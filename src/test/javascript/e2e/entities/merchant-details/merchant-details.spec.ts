import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MerchantDetailsComponentsPage, MerchantDetailsDeleteDialog, MerchantDetailsUpdatePage } from './merchant-details.page-object';

const expect = chai.expect;

describe('MerchantDetails e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let merchantDetailsComponentsPage: MerchantDetailsComponentsPage;
  let merchantDetailsUpdatePage: MerchantDetailsUpdatePage;
  let merchantDetailsDeleteDialog: MerchantDetailsDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load MerchantDetails', async () => {
    await navBarPage.goToEntity('merchant-details');
    merchantDetailsComponentsPage = new MerchantDetailsComponentsPage();
    await browser.wait(ec.visibilityOf(merchantDetailsComponentsPage.title), 5000);
    expect(await merchantDetailsComponentsPage.getTitle()).to.eq('Merchant Details');
    await browser.wait(
      ec.or(ec.visibilityOf(merchantDetailsComponentsPage.entities), ec.visibilityOf(merchantDetailsComponentsPage.noResult)),
      1000
    );
  });

  it('should load create MerchantDetails page', async () => {
    await merchantDetailsComponentsPage.clickOnCreateButton();
    merchantDetailsUpdatePage = new MerchantDetailsUpdatePage();
    expect(await merchantDetailsUpdatePage.getPageTitle()).to.eq('Create or edit a Merchant Details');
    await merchantDetailsUpdatePage.cancel();
  });

  it('should create and save MerchantDetails', async () => {
    const nbButtonsBeforeCreate = await merchantDetailsComponentsPage.countDeleteButtons();

    await merchantDetailsComponentsPage.clickOnCreateButton();

    await promise.all([
      merchantDetailsUpdatePage.setNameInput('name'),
      merchantDetailsUpdatePage.setCountryInput('country'),
      merchantDetailsUpdatePage.setCityInput('city'),
      merchantDetailsUpdatePage.setStoreIconInput('storeIcon'),
      merchantDetailsUpdatePage.setTypeInput('type'),
      merchantDetailsUpdatePage.locationSelectLastOption(),
    ]);

    await merchantDetailsUpdatePage.save();
    expect(await merchantDetailsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await merchantDetailsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last MerchantDetails', async () => {
    const nbButtonsBeforeDelete = await merchantDetailsComponentsPage.countDeleteButtons();
    await merchantDetailsComponentsPage.clickOnLastDeleteButton();

    merchantDetailsDeleteDialog = new MerchantDetailsDeleteDialog();
    expect(await merchantDetailsDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Merchant Details?');
    await merchantDetailsDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(merchantDetailsComponentsPage.title), 5000);

    expect(await merchantDetailsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
