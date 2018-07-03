package jna.office;

import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.COM.COMException;
import com.sun.jna.platform.win32.COM.COMLateBindingObject;
import com.sun.jna.platform.win32.COM.IDispatch;

public class PowerPoint extends Office {

	public PowerPoint() throws COMException {
		super("PowerPoint.Application");
		// setSelectionFontSize(30);
		// System.out.println(getSelectionFontSize());
		// setSelectionEmbossState(true);
		// duplicateActiveSlide();
		//System.out.println(getActiveSlideIndex());
		getActiveDocument();
	}

	public static void main(String[] args) {
		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		try {
			PowerPoint powerpoint = new PowerPoint();
			powerpoint.newSlide();
		} finally {
			Ole32.INSTANCE.CoUninitialize();
		}
	}

	@Override
	String getSelectedText() {
		return getTextRange(getSelection(getActiveWindow())).getStringProperty("Text");
	}

	@Override
	public void setSelectionBoldState(boolean state) {
		getFont(getTextRange(getSelection(getActiveWindow()))).setProperty("Bold", state);
	}

	@Override
	public void setSelectionItalicState(boolean state) {
		getFont(getTextRange(getSelection(getActiveWindow()))).setProperty("Italic", state);
	}

	@Override
	public void setSelectionUnderlineState(boolean state) {
		getFont(getTextRange(getSelection(getActiveWindow()))).setProperty("Underline", state);
	}

	@Override
	public void setSelectionStrikethroughState(boolean state) {
		getFont(getTextRange(getSelection(getActiveWindow()))).setProperty("Strikethrough", state);
	}

	public void setSelectionShadowState(boolean state) {
		getFont(getTextRange(getSelection(getActiveWindow()))).setProperty("Shadow", state);
	}

	public void setSelectionEmbossState(boolean state) {
		getFont(getTextRange(getSelection(getActiveWindow()))).setProperty("Emboss", state);
	}

	@Override
	public void setSelectionFontSize(int size) {
		getFont(getTextRange(getSelection(getActiveWindow()))).setProperty("Size", size);
	}

	public String getPathOfActiveDocument() {
		return getActiveDocument().getStringProperty("FullName");
	}

	public int getSelectionFontSize() {
		return getFont(getTextRange(getSelection(getActiveWindow()))).getIntProperty("Size");
	}

	public int getActiveSlideIndex() {
		return getActiveSlide().getIntProperty("SlideIndex");
	}

	// COMLateBindingObjects
	// -----------------------------------------------------------------------------------------------------

	private ActivePresentation getActiveDocument() throws COMException {
		return new ActivePresentation(this.getAutomationProperty("ActivePresentation"));
	}

	private Presentations getDocuments() throws COMException {
		return new Presentations(this.getAutomationProperty("Presentations"));
	}

	private View getActiveView() throws COMException {
		return new View(this.getAutomationProperty("View", getActiveWindow()));
	}

	private Slide getActiveSlide() throws COMException {
		return new Slide(this.getAutomationProperty("Slide", getActiveView()));
	}

	private Slides getSlides() throws COMException {
		return new Slides(this.getAutomationProperty("Slides", getActiveDocument()));
	}

	private TextRange getTextRange(Selection sel) throws COMException {
		return new TextRange(this.getAutomationProperty("TextRange", sel));
	}

	private CustomLayouts getCustomLayouts() {
		return new CustomLayouts(this.getAutomationProperty("CustomLayouts", getSlideMaster()));
	}
	
	private CustomLayout getDefaultCustomLayout() {
		return new CustomLayout(this.getAutomationProperty("Item", getCustomLayouts(), new VARIANT(1)));
	}

	private SlideMaster getSlideMaster() {
		return new SlideMaster(this.getAutomationProperty("SlideMaster", getActiveDocument()));
	}

	public boolean isActiveDocumentSaved() {
		if (getPathOfActiveDocument().contains(":")) {
			return true;
		}
		return false;
	}

	// Invokes
	// -----------------------------------------------------------------------------------------------------

	public void newDocument() throws COMException {
		this.invokeNoReply("Add", getDocuments());
	}

	public void duplicateActiveSlide() throws COMException {
		this.invokeNoReply("Duplicate", getActiveSlide());
	}

	public void newSlide() throws COMException {
		this.invokeNoReply("AddSlide", this.getSlides(), new VARIANT(0), new VARIANT(this.getActiveSlideIndex()));
	}

	public void deleteActiveSlide() throws COMException {
		this.invokeNoReply("Delete", getActiveSlide());
	}

	public void save(boolean bNoPrompt) throws COMException {
		this.invokeNoReply("Save", this.getDocuments(), new VARIANT(bNoPrompt));
	}

	// Classes
	// -------------------------------------------------------------------------------------------------------

	private class ActivePresentation extends COMLateBindingObject {
		public ActivePresentation(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	private class Presentations extends COMLateBindingObject {
		public Presentations(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	private class View extends COMLateBindingObject {
		public View(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	private class Slide extends COMLateBindingObject {
		public Slide(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	private class Slides extends COMLateBindingObject {
		public Slides(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	private class TextRange extends COMLateBindingObject {
		public TextRange(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	private class CustomLayouts extends COMLateBindingObject {
		public CustomLayouts(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}
	
	private class CustomLayout extends COMLateBindingObject {
		public CustomLayout(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	private class SlideMaster extends COMLateBindingObject {
		public SlideMaster(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}
}
